package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

/**
 * @author PurpleCrowbar
 */
public final class GwaihirBirdToken extends TokenImpl {

    public GwaihirBirdToken() {
        super("Bird Token", "3/3 white Bird creature token with flying and \"Whenever this creature attacks, target attacking creature gains flying until end of turn.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(3);
        toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, target attacking creature gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private GwaihirBirdToken(final GwaihirBirdToken token) {
        super(token);
    }

    @Override
    public GwaihirBirdToken copy() {
        return new GwaihirBirdToken(this);
    }
}
