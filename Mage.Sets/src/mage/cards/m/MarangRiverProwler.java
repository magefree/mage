
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public final class MarangRiverProwler extends CardImpl {

    public MarangRiverProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Marang River Prowler can't block and can't be blocked.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockSourceEffect(Duration.WhileOnBattlefield));
        Effect effect = new CantBeBlockedSourceEffect();
        effect.setText("and can't be blocked");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // You may cast Marang River Prowler from your graveyard as long as you control a black or green permanent.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new MarangRiverProwlerCastEffect()));
    }

    private MarangRiverProwler(final MarangRiverProwler card) {
        super(card);
    }

    @Override
    public MarangRiverProwler copy() {
        return new MarangRiverProwler(this);
    }
}

class MarangRiverProwlerCastEffect extends AsThoughEffectImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a black or green permanent");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.GREEN)));
    }

    MarangRiverProwlerCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard as long as you control a black or green permanent";
    }

    MarangRiverProwlerCastEffect(final MarangRiverProwlerCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MarangRiverProwlerCastEffect copy() {
        return new MarangRiverProwlerCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null 
                    && card.isOwnedBy(affectedControllerId)
                    && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                    && game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0) {
                return true;
            }
        }
        return false;
    }
}
