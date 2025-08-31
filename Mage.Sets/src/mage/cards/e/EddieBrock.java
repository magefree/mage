package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EddieBrock extends ModalDoubleFacedCard {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public EddieBrock(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.HERO, SubType.VILLAIN}, "{2}{B}",
                "Venom, Lethal Protector",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SYMBIOTE, SubType.HERO, SubType.VILLAIN}, "{3}{B}{R}{G}"
        );
        this.getLeftHalfCard().setPT(5, 5);
        this.getRightHalfCard().setPT(5, 5);

        // When Eddie Brock enters, return target creature card with mana value 1 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.getLeftHalfCard().addAbility(ability);

        // {3}{B}{R}{G}: Transform Eddie Brock. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{3}{B}{R}{G}")
        ));

        // Venom, Lethal Protector
        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever Venom attacks, you may sacrifice another creature. If you do, draw X cards, then you may put a permanent card with mana value X or less from your hand onto the battlefield, where X is the sacrificed creature's mana value.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new VenomLethalProtectorEffect()));
    }

    private EddieBrock(final EddieBrock card) {
        super(card);
    }

    @Override
    public EddieBrock copy() {
        return new EddieBrock(this);
    }
}

class VenomLethalProtectorEffect extends OneShotEffect {

    VenomLethalProtectorEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. If you do, draw X cards, " +
                "then you may put a permanent card with mana value X or less from your hand onto the battlefield, " +
                "where X is the sacrificed creature's mana value";
    }

    private VenomLethalProtectorEffect(final VenomLethalProtectorEffect effect) {
        super(effect);
    }

    @Override
    public VenomLethalProtectorEffect copy() {
        return new VenomLethalProtectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE);
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !player.chooseUse(Outcome.Sacrifice, "Sacrifice another creature?", source, game)
                || !cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int amount = cost
                .getPermanents()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        player.drawCards(amount, source, game);
        game.processAction();
        FilterCard filter = new FilterPermanentCard("permanent card with mana value " + amount + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, amount + 1));
        new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source);
        return true;
    }
}
