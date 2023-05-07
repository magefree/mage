package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 * @author PurpleCrowbar
 */
public final class WildfireAwakenerToken extends TokenImpl {

    public WildfireAwakenerToken() {
        super("Elemental Token", "1/1 red Elemental creature tokens with " +
                "\"Whenever this creature becomes tapped, it deals 1 damage to target player.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // Whenever this creature becomes tapped, it deals 1 damage to target player.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public WildfireAwakenerToken(final WildfireAwakenerToken token) {
        super(token);
    }

    @Override
    public WildfireAwakenerToken copy() {
        return new WildfireAwakenerToken(this);
    }
}
