
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class Scarecrow extends CardImpl {

    public Scarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {6}, {T}: Prevent all damage that would be dealt to you this turn by attacking creatures without flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScarecrowEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private Scarecrow(final Scarecrow card) {
        super(card);
    }

    @Override
    public Scarecrow copy() {
        return new Scarecrow(this);
    }
}

class ScarecrowEffect extends PreventionEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    ScarecrowEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to you this turn by creatures with flying";
    }

    private ScarecrowEffect(final ScarecrowEffect effect) {
        super(effect);
    }

    @Override
    public ScarecrowEffect copy() {
        return new ScarecrowEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamagePlayerEvent && event.getAmount() > 0) {
            DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
            if (event.getTargetId().equals(source.getControllerId())) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                if (permanent != null && filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
