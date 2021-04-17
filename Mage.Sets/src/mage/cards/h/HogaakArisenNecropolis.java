package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HogaakArisenNecropolis extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, -1));
    }

    public HogaakArisenNecropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B/G}{B/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // You can't spend mana to cast this spell.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("You can't spend mana to cast this spell")
        ));
        this.getSpellAbility().getManaCostsToPay().setSourceFilter(filter);
        this.getSpellAbility().getManaCosts().setSourceFilter(filter);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Delve
        this.addAbility(new DelveAbility());

        // You may cast Hogaak, Arisen Necropolis from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new HogaakArisenNecropolisEffect()));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private HogaakArisenNecropolis(final HogaakArisenNecropolis card) {
        super(card);
    }

    @Override
    public HogaakArisenNecropolis copy() {
        return new HogaakArisenNecropolis(this);
    }
}

class HogaakArisenNecropolisEffect extends AsThoughEffectImpl {

    HogaakArisenNecropolisEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may cast {this} from your graveyard";
    }

    private HogaakArisenNecropolisEffect(final HogaakArisenNecropolisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HogaakArisenNecropolisEffect copy() {
        return new HogaakArisenNecropolisEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(source.getSourceId())
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}
