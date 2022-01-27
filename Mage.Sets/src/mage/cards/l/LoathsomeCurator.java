package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoathsomeCurator extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control with mana value 3 or less");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public LoathsomeCurator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Exploit
        this.addAbility(new ExploitAbility());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Loathsome Curator exploits a creature, destroy target creature you don't control with mana value 3 or less.
        Ability ability = new ExploitCreatureTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LoathsomeCurator(final LoathsomeCurator card) {
        super(card);
    }

    @Override
    public LoathsomeCurator copy() {
        return new LoathsomeCurator(this);
    }
}
