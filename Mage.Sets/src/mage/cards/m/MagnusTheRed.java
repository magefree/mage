package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.SpawnToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagnusTheRed extends CardImpl {

    public MagnusTheRed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.PRIMARCH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Unearthly Power -- Instant and sorcery spells you cast cost {1} less to cast for each creature token you control.
        this.addAbility(new SimpleStaticAbility(new MagnusTheRedEffect())
                .withFlavorWord("Unearthly Power")
                .addHint(MagnusTheRedEffect.getHint()));

        // Blade of Magnus -- Whenever Magnus the Red deals combat damage to a player, create a 3/3 red Spawn creature token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new SpawnToken()), false
        ).withFlavorWord("Blade of Magnus"));
    }

    private MagnusTheRed(final MagnusTheRed card) {
        super(card);
    }

    @Override
    public MagnusTheRed copy() {
        return new MagnusTheRed(this);
    }
}

class MagnusTheRedEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creature tokens you control", xValue);

    MagnusTheRedEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "instant and sorcery spells you cast cost {1} less to cast for each creature token you control";
    }

    private MagnusTheRedEffect(final MagnusTheRedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, xValue.calculate(game, source, this));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())
                && ((SpellAbility) abilityToModify).getCharacteristics(game).isInstantOrSorcery(game)
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public MagnusTheRedEffect copy() {
        return new MagnusTheRedEffect(this);
    }

    public static Hint getHint() {
        return hint;
    }
}
