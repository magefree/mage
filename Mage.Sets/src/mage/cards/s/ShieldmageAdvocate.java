
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author L_J
 */
public final class ShieldmageAdvocate extends CardImpl {

    public ShieldmageAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Return target card from an opponent's graveyard to their hand. Prevent all damage that would be dealt to any target this turn by a source of your choice.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return target card from an opponent's graveyard to their hand");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());

        effect = new ShieldmageAdvocateEffect();
        effect.setTargetPointer(new SecondTargetPointer());
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInOpponentsGraveyard(1, 1, new FilterCard(), true));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ShieldmageAdvocate(final ShieldmageAdvocate card) {
        super(card);
    }

    @Override
    public ShieldmageAdvocate copy() {
        return new ShieldmageAdvocate(this);
    }
}

class ShieldmageAdvocateEffect extends PreventionEffectImpl {

    protected final TargetSource targetSource;

    public ShieldmageAdvocateEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to any target this turn by a source of your choice";
        this.targetSource = new TargetSource();
    }
    
    public ShieldmageAdvocateEffect(final ShieldmageAdvocateEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }
    
    @Override
    public ShieldmageAdvocateEffect copy() {
        return new ShieldmageAdvocateEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(targetPointer.getFirst(game, source)) && event.getSourceId().equals(targetSource.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }

}
