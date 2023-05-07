package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 * @author spjspj
 */
public final class TriskelaviteToken extends TokenImpl {

    public TriskelaviteToken() {
        super("Triskelavite Token", "1/1 colorless Triskelavite artifact creature token with flying. It has \"Sacrifice this creature: This creature deals 1 damage to any target.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TRISKELAVITE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        addAbility(FlyingAbility.getInstance());
    }

    public TriskelaviteToken(final TriskelaviteToken token) {
        super(token);
    }

    public TriskelaviteToken copy() {
        return new TriskelaviteToken(this);
    }
}
