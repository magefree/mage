package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RockslideSorcerer extends CardImpl {

    public RockslideSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant, sorcery, or Wizard spell, Rockslide Sorcerer deals 1 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(1), StaticFilters.FILTER_SPELL_INSTANT_SORCERY_WIZARD, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RockslideSorcerer(final RockslideSorcerer card) {
        super(card);
    }

    @Override
    public RockslideSorcerer copy() {
        return new RockslideSorcerer(this);
    }
}
