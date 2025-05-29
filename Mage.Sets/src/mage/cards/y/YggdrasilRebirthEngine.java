package mage.cards.y;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Momo2907
 */
public final class YggdrasilRebirthEngine extends CardImpl {

    public YggdrasilRebirthEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // When Yggdrasil, Rebirth Engine enters the battlefield, exile all creature cards from your graveyard.
        this.addAbility(
                new EntersBattlefieldTriggeredAbility(
                        new YggdrasilRebirthEngineExileAllCreaturesGraveyardEffect(),
                        false
                )
        );
        // {T}: Exile the top three cards of your library.
        this.addAbility(new SimpleActivatedAbility(
                new ExileCardsFromTopOfLibraryControllerEffect(3, true),
                new TapSourceCost()
        ));
        // {4}, {T}: Put a creature card exiled with Yggdrasil onto the battlefield under your control. It gains haste until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new YggdrasilRebirthEngineReturnCreatureEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{4}"));
        this.addAbility(ability);
    }

    private YggdrasilRebirthEngine(final YggdrasilRebirthEngine card) {
        super(card);
    }

    @Override
    public YggdrasilRebirthEngine copy() {
        return new YggdrasilRebirthEngine(this);
    }
}

class YggdrasilRebirthEngineExileAllCreaturesGraveyardEffect extends OneShotEffect{

    YggdrasilRebirthEngineExileAllCreaturesGraveyardEffect(){
        super(Outcome.Exile);
        staticText = "exile all creature cards from your graveyard";
    }

    private YggdrasilRebirthEngineExileAllCreaturesGraveyardEffect(final YggdrasilRebirthEngineExileAllCreaturesGraveyardEffect effect){
        super(effect);
    }

    @Override
    public YggdrasilRebirthEngineExileAllCreaturesGraveyardEffect copy(){
        return new YggdrasilRebirthEngineExileAllCreaturesGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source){
        Player controller = game.getPlayer(source.getControllerId());
        if(controller == null){
            return false;
        }
        UUID exileZoneId = CardUtil.getExileZoneId(game, source);
        String exileZoneName = CardUtil.createObjectRelatedWindowTitle(source, game, null);
        Cards cards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        if (cards.isEmpty()){
            return true;
        }
        controller.moveCardsToExile(cards.getCards(game), source, game, false, exileZoneId, exileZoneName);
        return true;
    }
}

class YggdrasilRebirthEngineReturnCreatureEffect extends OneShotEffect {

    public YggdrasilRebirthEngineReturnCreatureEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a creature card exiled with Yggdrasil onto the battlefield under your control. It gains haste until end of turn";
    }

    private YggdrasilRebirthEngineReturnCreatureEffect(final YggdrasilRebirthEngineReturnCreatureEffect effect){
        super(effect);
    }

    @Override
    public YggdrasilRebirthEngineReturnCreatureEffect copy() {
        return new YggdrasilRebirthEngineReturnCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source){
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exile == null || exile.isEmpty()) {
            return false;
        }
        TargetCardInExile target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exile.getId());
        target.withNotTarget(true);
        player.chooseTarget(outcome, exile, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null){
            return false;
        }
        if (!player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        Permanent movedCard = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (movedCard == null){
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(movedCard, game)), source);
        return true;
    }
}
