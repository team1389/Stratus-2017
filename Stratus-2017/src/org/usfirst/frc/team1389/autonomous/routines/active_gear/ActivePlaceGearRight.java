package org.usfirst.frc.team1389.autonomous.routines.active_gear;

import org.usfirst.frc.team1389.autonomous.AutoConstants;
import org.usfirst.frc.team1389.autonomous.command.RobotCommands;
import org.usfirst.frc.team1389.robot.RobotSoftware;
import org.usfirst.frc.team1389.systems.GearIntakeSystem;

import com.team1389.auto.AutoModeBase;
import com.team1389.auto.AutoModeEndedException;
import com.team1389.command_framework.CommandUtil;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

public class ActivePlaceGearRight extends AutoModeBase {
	private RobotSoftware robot;
	private RobotCommands commands;
	GearIntakeSystem gearIn;
	public ActivePlaceGearRight(RobotSoftware robot) {
		this.robot = robot;
		this.gearIn = new GearIntakeSystem(robot.armAngle, robot.armVel, robot.armElevator.getVoltageOutput(),
				robot.gearIntake.getVoltageOutput(), robot.gearIntakeCurrent);
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem.put(robot.voltageDrive);
	}

	@Override
	protected void routine() throws AutoModeEndedException {
		runCommand(CommandUtil.combineSequential(
				commands.new DriveStraight(AutoConstants.getRotations(AutoConstants.SIDE_GEAR_STRAIGHT)),
				commands.new TurnAngle(-AutoConstants.SIDE_GEAR_TURN, true),
				commands.new DriveStraight(AutoConstants.getRotations(AutoConstants.SIDE_GEAR_APPROACH - AutoConstants.ACTIVE_STOP_SHORT)),
				gearIn.placeGear()));
	}
	@Override
	public String getIdentifier() {
	return "Active Place Gear Right";
	}

}