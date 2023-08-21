package mage.cards.t;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TorrentialGearhulk extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant card from your graveyard");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public TorrentialGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Torrential Gearhulk enters the battlefield, you may cast target 
        // instant card from your graveyard without paying its mana cost.
        // If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TorrentialGearhulkEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private TorrentialGearhulk(final TorrentialGearhulk card) {
        super(card);
    }

    @Override
    public TorrentialGearhulk copy() {
        return new TorrentialGearhulk(this);
    }
}

class TorrentialGearhulkEffect extends OneShotEffect {

    TorrentialGearhulkEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast target instant card from your graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
    }

    TorrentialGearhulkEffect(final TorrentialGearhulkEffect effect) {
        super(effect);
    }

    @Override
    public TorrentialGearhulkEffect copy() {
        return new TorrentialGearhulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        if (controller.chooseUse(outcome, "Cast " + card.getLogName() + '?', source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
