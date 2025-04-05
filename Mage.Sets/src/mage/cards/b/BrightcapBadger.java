package mage.cards.b;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrightcapBadger extends AdventureCard {

    private static final FilterPermanent filter = new FilterPermanent("each Fungus and Saproling");

    static {
        filter.add(Predicates.or(
                SubType.FUNGUS.getPredicate(),
                SubType.SAPROLING.getPredicate()
        ));
    }

    public BrightcapBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{G}", "Fungus Frolic", "{2}{G}");

        this.subtype.add(SubType.BADGER);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Each Fungus and Saproling you control has "{T}: Add {G}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new GreenManaAbility(), Duration.WhileOnBattlefield, filter
        )));

        // At the beginning of your end step, create a 1/1 green Saproling token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken())
        ));

        // Fungus Frolic
        // Create two 1/1 green Saproling creature tokens.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), 2));
        this.finalizeAdventure();
    }

    private BrightcapBadger(final BrightcapBadger card) {
        super(card);
    }

    @Override
    public BrightcapBadger copy() {
        return new BrightcapBadger(this);
    }
}
