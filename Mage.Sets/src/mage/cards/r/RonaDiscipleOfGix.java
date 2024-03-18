
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterHistoricCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class RonaDiscipleOfGix extends CardImpl {

    public RonaDiscipleOfGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Rona, Disciple of Gix enters the battlefield, you may exile target historic card from your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ExileTargetEffect()
                        .setToSourceExileZone(true)
                        .setText("exile target historic card from your graveyard. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                true
        );
        ability.addTarget(new TargetCardInYourGraveyard(new FilterHistoricCard("historic card from your graveyard")));
        this.addAbility(ability);

        // You may cast nonland cards exiled with Rona.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RonaDiscipleOfGixPlayNonLandEffect()));

        // {4}, {T}: Exile the top card of your library.
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileCardsFromTopOfLibraryControllerEffect(1, true),
                new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RonaDiscipleOfGix(final RonaDiscipleOfGix card) {
        super(card);
    }

    @Override
    public RonaDiscipleOfGix copy() {
        return new RonaDiscipleOfGix(this);
    }
}

class RonaDiscipleOfGixPlayNonLandEffect extends AsThoughEffectImpl {

    RonaDiscipleOfGixPlayNonLandEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast nonland cards exiled with {this}";
    }

    private RonaDiscipleOfGixPlayNonLandEffect(final RonaDiscipleOfGixPlayNonLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RonaDiscipleOfGixPlayNonLandEffect copy() {
        return new RonaDiscipleOfGixPlayNonLandEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            MageObject sourceObject = game.getObject(source);
            if (card != null && !card.isLand(game) && sourceObject != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
                if (exileId != null) {
                    ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                    return exileZone != null && exileZone.contains(objectId);
                }
            }
        }
        return false;
    }
}
