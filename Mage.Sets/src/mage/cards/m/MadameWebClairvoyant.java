package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class MadameWebClairvoyant extends CardImpl {

    private static final FilterCard filter = new FilterCard("cast Spider spells and noncreature spells");

    static {
        filter.add(Predicates.or(
                Predicates.not(CardType.CREATURE.getPredicate()),
                SubType.SPIDER.getPredicate()
        ));
    }

    public MadameWebClairvoyant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast Spider spells and noncreature spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // Whenever you attack, you may mill a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                Zone.BATTLEFIELD, new MillCardsControllerEffect(1),
                1, StaticFilters.FILTER_PERMANENT_CREATURES, false, true)
        );
    }

    private MadameWebClairvoyant(final MadameWebClairvoyant card) {
        super(card);
    }

    @Override
    public MadameWebClairvoyant copy() {
        return new MadameWebClairvoyant(this);
    }
}
