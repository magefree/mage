package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Xanderhall
 */
public final class BrenardGingerSculptor extends CardImpl {

    private static final FilterCreaturePermanent foodOrGolemCreature = new FilterCreaturePermanent("a Food or a Golem creature");
    private static final FilterPermanent nontokenCreature = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        foodOrGolemCreature.add(Predicates.or(SubType.FOOD.getPredicate(), SubType.GOLEM.getPredicate()));

        nontokenCreature.add(TokenPredicate.FALSE);
        nontokenCreature.add(AnotherPredicate.instance);
    }

    public BrenardGingerSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each creature you control that's a Food or a Golem gets +2/+2 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, foodOrGolemCreature)
            .setText("each creature you control that's a Food or a Golem gets +2/+2"));
        ability.addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, foodOrGolemCreature).setText("and has trample."));
        this.addAbility(ability);

        // Whenever another nontoken creature you control dies, you may exile it. If you do, create a token that's a copy of that creature, except it's a 1/1 Food Golem artifact creature in addition to its other types and it has "2, {T}, Sacrifice this artifact: You gain 3 life."
        this.addAbility(new DiesCreatureTriggeredAbility(new BrenardGingerSculptorEffect(), true, nontokenCreature, true));

    }

    private BrenardGingerSculptor(final BrenardGingerSculptor card) {
        super(card);
    }

    @Override
    public BrenardGingerSculptor copy() {
        return new BrenardGingerSculptor(this);
    }
}

class BrenardGingerSculptorEffect extends OneShotEffect {

    BrenardGingerSculptorEffect() {
        super(Outcome.Copy);
        this.staticText = "you may exile it. If you do, create a token that's a copy of that creature, except it's a 1/1 Food Golem artifact creature in "+ 
        "addition to its other types and it has \"{2}, {T}, Sacrifice this artifact: You gain 3 life.\"";
    }

    private BrenardGingerSculptorEffect(final BrenardGingerSculptorEffect effect) {
        super(effect);
    }

    public BrenardGingerSculptorEffect copy() {
        return new BrenardGingerSculptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), CardType.CREATURE, false, 1, false,
                false, null, 1, 1, false
        );
        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game) + 1));
        effect.setBecomesArtifact(true);
        effect.withAdditionalSubType(SubType.FOOD);
        effect.withAdditionalSubType(SubType.GOLEM);
        effect.addAdditionalAbilities(new FoodAbility(false));

        player.moveCards(card, Zone.EXILED, source, game);
        effect.apply(game, source);
        return true;
    }

    
}
