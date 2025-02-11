package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author L_J
 */
public final class NaturesWrath extends CardImpl {
    private static final FilterPermanent filterBlue = new FilterPermanent("an Island or blue permanent");
    static{
        filterBlue.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), SubType.ISLAND.getPredicate()));
    }

    private static final FilterPermanent filterBlack = new FilterPermanent("a Swamp or black permanent");
    static{
        filterBlack.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), SubType.SWAMP.getPredicate()));
    }

    public NaturesWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // At the beginning of your upkeep, sacrifice Nature's Wrath unless you pay {G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{G}"))));

        // Whenever a player puts an Island or blue permanent onto the battlefield, they sacrifice an Island or blue permanent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new SacrificeEffect(filterBlue, 1, "that player"),
                filterBlue,
                false, SetTargetPointer.PLAYER
        ).setTriggerPhrase("Whenever a player puts an Island or blue permanent onto the battlefield, "));

        // Whenever a player puts a Swamp or black permanent onto the battlefield, they sacrifice a Swamp or black permanent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new SacrificeEffect(filterBlack, 1, "that player"),
                filterBlack,
                false, SetTargetPointer.PLAYER
        ).setTriggerPhrase("Whenever a player puts a Swamp or black permanent onto the battlefield, "));
    }

    private NaturesWrath(final NaturesWrath card) {
        super(card);
    }

    @Override
    public NaturesWrath copy() {
        return new NaturesWrath(this);
    }
}
