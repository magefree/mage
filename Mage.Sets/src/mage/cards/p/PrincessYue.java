package mage.cards.p;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrincessYue extends CardImpl {

    public PrincessYue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Princess Yue dies, if she was a nonland creature, return this card to the battlefield tapped under your control. She's a land named Moon. She gains "{T}: Add {C}."
        this.addAbility(new PrincessYueTriggeredAbility());

        // {T}: Scry 2.
        this.addAbility(new SimpleActivatedAbility(new ScryEffect(2), new TapSourceCost()));
    }

    private PrincessYue(final PrincessYue card) {
        super(card);
    }

    @Override
    public PrincessYue copy() {
        return new PrincessYue(this);
    }
}

class PrincessYueTriggeredAbility extends DiesSourceTriggeredAbility {

    PrincessYueTriggeredAbility() {
        super(new PrincessYueReturnEffect());
    }

    private PrincessYueTriggeredAbility(final PrincessYueTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PrincessYueTriggeredAbility copy() {
        return new PrincessYueTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return CardUtil
                .getEffectValueFromAbility(this, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> !permanent.isLand(game) && permanent.isCreature(game))
                .isPresent();
    }
}

class PrincessYueReturnEffect extends OneShotEffect {

    PrincessYueReturnEffect() {
        super(Outcome.Benefit);
        staticText = "if she was a nonland creature, return this card to the battlefield tapped " +
                "under your control. She's a land named Moon. She gains \"{T}: Add {C}.\"";
    }

    private PrincessYueReturnEffect(final PrincessYueReturnEffect effect) {
        super(effect);
    }

    @Override
    public PrincessYueReturnEffect copy() {
        return new PrincessYueReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        game.addEffect(new PrincessYueTypeEffect()
                .setTargetPointer(new FixedTarget(new MageObjectReference(card, game, 1))), source);
        return player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
    }
}

class PrincessYueTypeEffect extends ContinuousEffectImpl {

    PrincessYueTypeEffect() {
        super(Duration.Custom, Outcome.Benefit);
    }

    private PrincessYueTypeEffect(final PrincessYueTypeEffect effect) {
        super(effect);
    }

    @Override
    public PrincessYueTypeEffect copy() {
        return new PrincessYueTypeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TextChangingEffects_3:
                permanent.setName("Moon");
                return true;
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.LAND);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(new ColorlessManaAbility(), source.getSourceId(), game);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TextChangingEffects_3:
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
