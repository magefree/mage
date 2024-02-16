package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScreamerKiller extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("a creature spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public ScreamerKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Bio-Plasmic Scream -- Whenever you cast a creature spell with mana value 5 or greater, Screamer-Killer deals 5 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(5), filter, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.withFlavorWord("Bio-Plasmic Scream"));
    }

    private ScreamerKiller(final ScreamerKiller card) {
        super(card);
    }

    @Override
    public ScreamerKiller copy() {
        return new ScreamerKiller(this);
    }
}
