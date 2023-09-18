package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KumenasSpeaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Merfolk or an Island");

    static {
        filter.add(KumenasSpeakerPredicate.instance);
    }

    public KumenasSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kumena's Omenspeaker gets +1/+1 as long as you control another Merfolk or Island.
        this.addAbility(new SimpleStaticAbility(new BoostSourceWhileControlsEffect(filter, 1, 1)));
    }

    private KumenasSpeaker(final KumenasSpeaker card) {
        super(card);
    }

    @Override
    public KumenasSpeaker copy() {
        return new KumenasSpeaker(this);
    }
}

enum KumenasSpeakerPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject obj = input.getObject();
        if (obj.getId().equals(input.getSourceId())) {
            return obj.hasSubtype(SubType.ISLAND, game);
        }
        return obj.hasSubtype(SubType.ISLAND, game)
                || obj.hasSubtype(SubType.MERFOLK, game);
    }
}
