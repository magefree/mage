
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public final class FiendOfTheShadows extends CardImpl {

    private UUID exileId = UUID.randomUUID();

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Human");

    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public FiendOfTheShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Fiend of the Shadows deals combat damage to a player, that player exiles a card from their hand. You may play that card for as long as it remains exiled.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ExileFromZoneTargetEffect(Zone.HAND, exileId, "Fiend of the Shadows", new FilterCard()), false, true));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new FiendOfTheShadowsEffect(exileId)));

        // Sacrifice a Human: Regenerate Fiend of the Shadows.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false))));
    }

    public FiendOfTheShadows(final FiendOfTheShadows card) {
        super(card);
    }

    @Override
    public FiendOfTheShadows copy() {
        return new FiendOfTheShadows(this);
    }
}

class FiendOfTheShadowsEffect extends AsThoughEffectImpl {

    private final UUID exileId;

    public FiendOfTheShadowsEffect(UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.exileId = exileId;
        staticText = "You may play that card for as long as it remains exiled";
    }

    public FiendOfTheShadowsEffect(final FiendOfTheShadowsEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public FiendOfTheShadowsEffect copy() {
        return new FiendOfTheShadowsEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            ExileZone zone = game.getExile().getExileZone(exileId);
            if (zone != null && zone.contains(objectId)) {
                return true;
            }
        }
        return false;
    }

}
