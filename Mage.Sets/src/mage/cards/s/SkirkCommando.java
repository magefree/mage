
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class SkirkCommando extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature that player controls");

    public SkirkCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //Whenever Skirk Commando deals combat damage to a player, you may have it deal 2 damage to target creature that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DamageTargetEffect(2), true, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);

        //Morph {2}{R} (You may cast this card face down as a 2/2 creature for 3. Turn it face up any time for its morph cost.)
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{2}{R}")));

    }

    private SkirkCommando(final SkirkCommando card) {
        super(card);
    }

    @Override
    public SkirkCommando copy() {
        return new SkirkCommando(this);
    }
}
