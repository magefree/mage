package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class GideonJura extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public GideonJura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        this.setStartingLoyalty(6);

        // +2: During target opponent's next turn, creatures that player controls attack Gideon Jura if able.
        LoyaltyAbility ability1 = new LoyaltyAbility(new GideonJuraEffect(), 2);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // −2: Destroy target tapped creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);

        // 0: Until end of turn, Gideon Jura becomes a 6/6 Human Soldier creature that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability3 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonJuraToken(), "planeswalker", Duration.EndOfTurn), 0);
        Effect effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability3.addEffect(effect);
        this.addAbility(ability3);
    }

    private GideonJura(final GideonJura card) {
        super(card);
    }

    @Override
    public GideonJura copy() {
        return new GideonJura(this);
    }

}

class GideonJuraToken extends TokenImpl {

    public GideonJuraToken() {
        super("", "6/6 Human Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(6);
        toughness = new MageInt(6);
    }

    public GideonJuraToken(final GideonJuraToken token) {
        super(token);
    }

    @Override
    public GideonJuraToken copy() {
        return new GideonJuraToken(this);
    }
}

class GideonJuraEffect extends RequirementEffect {

    protected MageObjectReference creatingPermanent;

    public GideonJuraEffect() {
        super(Duration.Custom);
        staticText = "During target opponent's next turn, creatures that player controls attack {this} if able";
    }

    public GideonJuraEffect(final GideonJuraEffect effect) {
        super(effect);
        this.creatingPermanent = effect.creatingPermanent;
    }

    @Override
    public GideonJuraEffect copy() {
        return new GideonJuraEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        creatingPermanent = new MageObjectReference(source.getSourceId(), game);
        setStartingControllerAndTurnNum(game, source.getFirstTarget(), game.getActivePlayerId()); // setup startingController to calc isYourTurn calls
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(source.getFirstTarget()) && this.isYourNextTurn(game);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return (game.getTurnPhaseType() == TurnPhase.END && this.isYourNextTurn(game))
                // 6/15/2010: If a creature controlled by the affected player can't attack Gideon Jura
                // (because he's no longer on the battlefield, for example), that player may have it attack you,
                // another one of your planeswalkers, or nothing at all.
                || creatingPermanent.getPermanent(game) == null;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return source.getSourceId();
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}
