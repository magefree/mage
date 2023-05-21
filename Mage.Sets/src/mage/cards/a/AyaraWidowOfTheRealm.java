package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterOpponent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetPermanentOrPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyaraWidowOfTheRealm extends TransformingDoubleFacedCard {

    private static final DynamicValue xValue = new SacrificeCostConvertedMana("permanent");
    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer(
            "opponent or battle", StaticFilters.FILTER_PERMANENT_BATTLE, new FilterOpponent()
    );
    private static final FilterCard filter2 = new FilterCard("artifact or creature card from your graveyard");

    static {
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public AyaraWidowOfTheRealm(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.NOBLE}, "{1}{B}{B}",
                "Ayara, Furnace Queen",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.ELF, SubType.NOBLE}, "BR"
        );
        this.getLeftHalfCard().setPT(3, 3);
        this.getRightHalfCard().setPT(4, 4);

        // {T}, Sacrifice another creature or artifact: Ayara, Widow of the Realm deals X damage to target opponent or battle and you gain X life, where X is the sacrificed permanent's mana value.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to target opponent or battle"), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        ability.addEffect(new GainLifeEffect(xValue).setText("and you gain X life, where X is the sacrificed permanent's mana value"));
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.getLeftHalfCard().addAbility(ability);

        // {5}{R/P}: Transform Ayara. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{5}{R/P}")
        ));

        // Ayara, Furnace Queen
        // At the beginning of combat on your turn, return up to one target artifact or creature card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step.
        ability = new BeginningOfCombatTriggeredAbility(
                new AyaraFurnaceQueenEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
        this.getRightHalfCard().addAbility(ability);
    }

    private AyaraWidowOfTheRealm(final AyaraWidowOfTheRealm card) {
        super(card);
    }

    @Override
    public AyaraWidowOfTheRealm copy() {
        return new AyaraWidowOfTheRealm(this);
    }
}

class AyaraFurnaceQueenEffect extends OneShotEffect {

    AyaraFurnaceQueenEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one target artifact or creature card from your graveyard " +
                "to the battlefield. It gains haste. Exile it at the beginning of the next end step";
    }

    private AyaraFurnaceQueenEffect(final AyaraFurnaceQueenEffect effect) {
        super(effect);
    }

    @Override
    public AyaraFurnaceQueenEffect copy() {
        return new AyaraFurnaceQueenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        if (game.getPermanent(card.getId()) == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTarget(card.getId(), game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ExileTargetEffect().setText("exile it")
                        .setTargetPointer(new FixedTarget(card.getId(), game)),
                TargetController.ANY
        ), source);
        return true;
    }
}
