package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class AuriokReplica extends CardImpl {

    public AuriokReplica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, Sacrifice Auriok Replica: Prevent all damage a source of your choice would deal to you this turn.
        Ability ability = new SimpleActivatedAbility(new AuriokReplicaEffect(), new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private AuriokReplica(final AuriokReplica card) {
        super(card);
    }

    @Override
    public AuriokReplica copy() {
        return new AuriokReplica(this);
    }
}

class AuriokReplicaEffect extends PreventionEffectImpl {

    private final TargetSource target;

    public AuriokReplicaEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "prevent all damage a source of your choice would deal to you this turn";
        this.target = new TargetSource();
    }

    public AuriokReplicaEffect(final AuriokReplicaEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public AuriokReplicaEffect copy() {
        return new AuriokReplicaEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return (super.applies(event, source, game)
                && event.getTargetId().equals(source.getControllerId())
                && event.getSourceId().equals(target.getFirstTarget())
        );
    }
}
