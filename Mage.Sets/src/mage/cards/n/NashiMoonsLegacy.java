package mage.cards.n;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NashiMoonsLegacy extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary or Rat card from your graveyard");

    static {
        filter.add(Predicates.or(
                SuperType.LEGENDARY.getPredicate(),
                SubType.RAT.getPredicate()
        ));
    }

    public NashiMoonsLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // Whenever Nashi, Moon's Legacy attacks, exile up to one target legendary or Rat card from your graveyard and copy it. You may cast the copy.
        Ability ability = new AttacksTriggeredAbility(new NashiMoonsLegacyEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private NashiMoonsLegacy(final NashiMoonsLegacy card) {
        super(card);
    }

    @Override
    public NashiMoonsLegacy copy() {
        return new NashiMoonsLegacy(this);
    }
}

class NashiMoonsLegacyEffect extends OneShotEffect {

    NashiMoonsLegacyEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target legendary or Rat card from your graveyard and copy it. You may cast the copy";
    }

    private NashiMoonsLegacyEffect(final NashiMoonsLegacyEffect effect) {
        super(effect);
    }

    @Override
    public NashiMoonsLegacyEffect copy() {
        return new NashiMoonsLegacyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card copiedCard = game.copyCard(card, source, player.getId());
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, false),
                game, false, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
