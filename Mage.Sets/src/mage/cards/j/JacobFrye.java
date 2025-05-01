package mage.cards.j;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JacobFrye extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ASSASSIN, "Assassins you control");
    private static final FilterCard filter2 = new FilterCard("Assassin card or card with freerunning");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(Predicates.or(
                SubType.ASSASSIN.getPredicate(),
                new AbilityPredicate(FreerunningAbility.class)
        ));
    }

    public JacobFrye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Partner with Evie Frye
        this.addAbility(new PartnerWithAbility("Evie Frye"));

        // Whenever one or more Assassins you control deal combat damage to a player, exile up to one target Assassin card or card with freerunning from your graveyard. If you do, copy it. You may cast the copy.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(new JacobFryeEffect(), filter);
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
        this.addAbility(ability);
    }

    private JacobFrye(final JacobFrye card) {
        super(card);
    }

    @Override
    public JacobFrye copy() {
        return new JacobFrye(this);
    }
}

class JacobFryeEffect extends OneShotEffect {

    JacobFryeEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target Assassin card or card with freerunning " +
                "from your graveyard. If you do, copy it. You may cast the copy";
    }

    private JacobFryeEffect(final JacobFryeEffect effect) {
        super(effect);
    }

    @Override
    public JacobFryeEffect copy() {
        return new JacobFryeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        copiedCard.setZone(Zone.OUTSIDE, game);
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, false),
                game, false, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
