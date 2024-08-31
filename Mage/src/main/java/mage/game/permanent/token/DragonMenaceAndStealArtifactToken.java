package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

public class DragonMenaceAndStealArtifactToken extends TokenImpl {

    private static final FilterArtifactPermanent filter
            = new FilterArtifactPermanent("artifact that player controls");

    public DragonMenaceAndStealArtifactToken() {
        super("Dragon Token", "6/6 black and red Dragon creature token with flying, menace, and \"Whenever this creature deals combat damage to a player, gain control of target artifact that player controls.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
        addAbility(new MenaceAbility(false));

        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new GainControlTargetEffect(Duration.EndOfGame), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        addAbility(ability);
    }

    private DragonMenaceAndStealArtifactToken(final DragonMenaceAndStealArtifactToken token) {
        super(token);
    }

    public DragonMenaceAndStealArtifactToken copy() {
        return new DragonMenaceAndStealArtifactToken(this);
    }

}
