package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class MercenaryToken extends TokenImpl {

    public MercenaryToken() {
        super("Mercenary Token", "1/1 red Mercenary creature token with \"{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.MERCENARY);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(1, 0), new TapSourceCost()
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private MercenaryToken(final MercenaryToken token) {
        super(token);
    }

    public MercenaryToken copy() {
        return new MercenaryToken(this);
    }
}
