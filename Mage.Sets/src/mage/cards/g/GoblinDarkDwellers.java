package mage.cards.g;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class GoblinDarkDwellers extends CardImpl {

    private static final FilterInstantOrSorceryCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GoblinDarkDwellers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Goblin Dark-Dwellers enters the battlefield, you may cast target instant or sorcery card with converted mana cost 3 or less
        // from your graveyard without paying its mana cost. If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GoblinDarkDwellersEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private GoblinDarkDwellers(final GoblinDarkDwellers card) {
        super(card);
    }

    @Override
    public GoblinDarkDwellers copy() {
        return new GoblinDarkDwellers(this);
    }
}

class GoblinDarkDwellersEffect extends OneShotEffect {

    GoblinDarkDwellersEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast target instant or sorcery card with "
                + "mana value 3 or less from your graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
    }

    GoblinDarkDwellersEffect(final GoblinDarkDwellersEffect effect) {
        super(effect);
    }

    @Override
    public GoblinDarkDwellersEffect copy() {
        return new GoblinDarkDwellersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        if (controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getLogName() + '?', source, game)) {
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
