package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KitsaOtterballElite extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("{this}'s power is 3 or greater");
    private static final FilterSpell filter2 = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public KitsaOtterballElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // {T}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new TapSourceCost()
        ));

        // {2}, {T}: Copy target instant or sorcery spell you control. You may choose new targets for the copy. Activate only if Kitsa's power is 3 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new CopyTargetStackObjectEffect(), new GenericManaCost(2), condition
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter2));
        this.addAbility(ability);
    }

    private KitsaOtterballElite(final KitsaOtterballElite card) {
        super(card);
    }

    @Override
    public KitsaOtterballElite copy() {
        return new KitsaOtterballElite(this);
    }
}
