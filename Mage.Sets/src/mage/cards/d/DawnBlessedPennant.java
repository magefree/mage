package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnBlessedPennant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a permanent you control of the chosen type");
    private static final FilterCard filter2 = new FilterCard("card of the chosen type from your graveyard");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
        filter2.add(ChosenSubtypePredicate.TRUE);
    }

    public DawnBlessedPennant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // As this artifact enters, choose Elemental, Elf, Faerie, Giant, Goblin, Kithkin, Merfolk, or Treefolk.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(
                Outcome.Benefit, SubType.ELEMENTAL, SubType.ELF, SubType.FAERIE, SubType.GIANT,
                SubType.GOBLIN, SubType.KITHKIN, SubType.MERFOLK, SubType.TREEFOLK
        )));

        // Whenever a permanent you control of the chosen type enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new GainLifeEffect(1), filter));

        // {2}, {T}, Sacrifice this artifact: Return target card of the chosen type from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private DawnBlessedPennant(final DawnBlessedPennant card) {
        super(card);
    }

    @Override
    public DawnBlessedPennant copy() {
        return new DawnBlessedPennant(this);
    }
}
