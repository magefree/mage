package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class NullhideFerox extends CardImpl {

    public NullhideFerox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // You can't cast noncreature spells.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new NullhideFeroxCantCastEffect()
        ));

        // {2}: Nullhide Ferox loses all abilities until end of turn. Any player may activate this ability.
        ActivatedAbility ability = new SimpleActivatedAbility(
                new NullhideFeroxLoseAbilitiesEffect(), new GenericManaCost(2)
        );
        ability.setMayActivate(TargetController.ANY);
        this.addAbility(ability);

        // If a spell or ability an opponent controls causes you to discard Nullhide Ferox, put it onto the battlefield instead of putting it into your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.HAND, new DiscardOntoBattlefieldEffect()
        ));
    }

    private NullhideFerox(final NullhideFerox card) {
        super(card);
    }

    @Override
    public NullhideFerox copy() {
        return new NullhideFerox(this);
    }
}

class NullhideFeroxCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public NullhideFeroxCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast noncreature spells";
    }

    private NullhideFeroxCantCastEffect(final NullhideFeroxCantCastEffect effect) {
        super(effect);
    }

    @Override
    public NullhideFeroxCantCastEffect copy() {
        return new NullhideFeroxCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Card card = game.getCard(event.getSourceId());
            return card != null && !card.isCreature(game);
        }
        return false;
    }

}

class NullhideFeroxLoseAbilitiesEffect extends OneShotEffect {

    public NullhideFeroxLoseAbilitiesEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} loses all abilities until end of turn. Any player may activate this ability";
    }

    private NullhideFeroxLoseAbilitiesEffect(final NullhideFeroxLoseAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public NullhideFeroxLoseAbilitiesEffect copy() {
        return new NullhideFeroxLoseAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ContinuousEffect effect = new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        game.addEffect(effect, source);
        return true;
    }
}
