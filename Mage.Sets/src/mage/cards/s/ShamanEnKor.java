
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.RedirectionEffect.UsageType;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSource;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class ShamanEnKor extends CardImpl {

    public ShamanEnKor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {0}: The next 1 damage that would be dealt to Shaman en-Kor this turn is dealt to target creature you control instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RedirectDamageFromSourceToTargetEffect(Duration.EndOfTurn, 1, UsageType.ONE_USAGE_ABSOLUTE), new GenericManaCost(0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {1}{W}: The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to Shaman en-Kor instead.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShamanEnKorRedirectFromTargetEffect(), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ShamanEnKor(final ShamanEnKor card) {
        super(card);
    }

    @Override
    public ShamanEnKor copy() {
        return new ShamanEnKor(this);
    }
}

class ShamanEnKorRedirectFromTargetEffect extends RedirectionEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();
    protected MageObjectReference sourceObject;

    ShamanEnKorRedirectFromTargetEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to {this} instead";
    }

    ShamanEnKorRedirectFromTargetEffect(final ShamanEnKorRedirectFromTargetEffect effect) {
        super(effect);
        sourceObject = effect.sourceObject;
    }

    @Override
    public void init(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetSource target = new TargetSource();
            target.choose(Outcome.PreventDamage, player.getId(), source.getSourceId(), source, game);
            this.sourceObject = new MageObjectReference(target.getFirstTarget(), game);
        } else {
            discard();
        }
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (filter.match(permanent, permanent.getControllerId(), source, game)) {
                if (sourceObject.equals(new MageObjectReference(event.getSourceId(), game))) {
                    redirectTarget = new TargetPermanent();
                    redirectTarget.add(source.getSourceId(), game);
                    return event.getTargetId().equals(getTargetPointer().getFirst(game, source));
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ShamanEnKorRedirectFromTargetEffect copy() {
        return new ShamanEnKorRedirectFromTargetEffect(this);
    }
}
