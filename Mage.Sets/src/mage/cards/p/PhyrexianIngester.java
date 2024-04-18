
package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author North, Susucr
 */
public final class PhyrexianIngester extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public PhyrexianIngester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Imprint - When Phyrexian Ingester enters the battlefield, you may exile target nontoken creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PhyrexianIngesterImprintEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.IMPRINT));
        // Phyrexian Ingester gets +X/+Y, where X is the exiled creature card's power and Y is its toughness.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianIngesterBoostEffect()));
    }

    private PhyrexianIngester(final PhyrexianIngester card) {
        super(card);
    }

    @Override
    public PhyrexianIngester copy() {
        return new PhyrexianIngester(this);
    }
}

class PhyrexianIngesterImprintEffect extends OneShotEffect {

    PhyrexianIngesterImprintEffect() {
        super(Outcome.Exile);
        this.staticText = "exile target nontoken creature";
    }

    private PhyrexianIngesterImprintEffect(final PhyrexianIngesterImprintEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianIngesterImprintEffect copy() {
        return new PhyrexianIngesterImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null && sourceObject == null) {
            return false;
        }
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent == null) {
            return false;
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null) {
            UUID exileZoneId = CardUtil.getCardExileZoneId(game, source);
            String exileZoneName = sourceObject.getIdName() + " (Imprint)";
            controller.moveCardsToExile(targetPermanent, source, game, true, exileZoneId, exileZoneName);
            sourcePermanent.imprint(targetPermanent.getId(), game);
        } else {
            // Ingester is no longer on the battlefield, but the target still needs to be exiled.
            // no need for specific exile zone
            controller.moveCardsToExile(targetPermanent, source, game, true, null, "");
        }
        return true;
    }
}

class PhyrexianIngesterBoostEffect extends ContinuousEffectImpl {

    PhyrexianIngesterBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "{this} gets +X/+Y, where X is the exiled creature card's power and Y is its toughness";
    }

    private PhyrexianIngesterBoostEffect(final PhyrexianIngesterBoostEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianIngesterBoostEffect copy() {
        return new PhyrexianIngesterBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        for (UUID cardId : permanent.getImprinted()) {
            Card card = game.getCard(cardId);
            if (card != null) {
                permanent.addPower(card.getPower().getValue());
                permanent.addToughness(card.getToughness().getValue());
            }
        }
        return true;
    }
}
