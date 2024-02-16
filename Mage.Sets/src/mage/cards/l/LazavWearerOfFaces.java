package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author DominionSpy
 */
public final class LazavWearerOfFaces extends CardImpl {

    public LazavWearerOfFaces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Lazav, Wearer of Faces attacks, exile target card from a graveyard, then investigate.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true));
        ability.addEffect(new InvestigateEffect().concatBy(", then"));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Whenever you sacrifice a Clue, you may have Lazav become a copy of a creature card exiled with it until end of turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(new LazavWearerOfFacesEffect(),
                StaticFilters.FILTER_CONTROLLED_CLUE));
    }

    private LazavWearerOfFaces(final LazavWearerOfFaces card) {
        super(card);
    }

    @Override
    public LazavWearerOfFaces copy() {
        return new LazavWearerOfFaces(this);
    }
}

class LazavWearerOfFacesEffect extends OneShotEffect {

    LazavWearerOfFacesEffect() {
        super(Outcome.Copy);
        staticText = "you may have {this} become a copy of a creature card exiled with it until end of turn";
    }

    private LazavWearerOfFacesEffect(final LazavWearerOfFacesEffect effect) {
        super(effect);
    }

    @Override
    public LazavWearerOfFacesEffect copy() {
        return new LazavWearerOfFacesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent lazav = game.getPermanent(source.getSourceId());
        if (controller == null || lazav == null) {
            return false;
        }

        if (!controller.chooseUse(outcome, "Have {this} become a copy of a creature card exiled with it", source, game)) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exile = game.getExile().getExileZone(exileId);
        if (exile == null) {
            return false;
        }
        Cards cards = new CardsImpl(exile.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        TargetCard target = new TargetCard(Zone.EXILED, new FilterCard("creature card to copy"));
        target.withNotTarget(true);
        controller.chooseTarget(outcome, cards, target, source, game);

        Card copyFromCard = game.getCard(target.getFirstTarget());
        if (copyFromCard == null) {
            return false;
        }

        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, copyFromCard, lazav.getId());
        copyEffect.newId();
        game.addEffect(copyEffect, source);

        return true;
    }
}
