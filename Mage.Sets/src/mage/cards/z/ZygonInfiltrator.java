package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Skiwkr
 */
public final class ZygonInfiltrator extends CardImpl {

    public ZygonInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Body-print -- {2}{U}: Tap another target creature and put a stun counter on it. Zygon Infiltrator becomes a copy of that creature for as long as that creature remains tapped. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(1)).setText("and put a stun counter on it"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        ability.addEffect(new ZygonInfiltratorEffect());
        this.addAbility(ability.withFlavorWord("Body-print"));
    }

    private ZygonInfiltrator(final ZygonInfiltrator card) {
        super(card);
    }

    @Override
    public ZygonInfiltrator copy() {
        return new ZygonInfiltrator(this);
    }
}

class ZygonInfiltratorEffect extends OneShotEffect {

    ZygonInfiltratorEffect() {
        super(Outcome.Copy);
        staticText = "{this} becomes a copy of that creature for as long as " +
                "that creature remains tapped.";
    }

    private ZygonInfiltratorEffect(final ZygonInfiltratorEffect effect) {
        super(effect);
    }

    @Override
    public ZygonInfiltratorEffect copy() {
        return new ZygonInfiltratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent tappedCreature = game.getPermanent(source.getFirstTarget());
        Permanent sourceCreature = source.getSourcePermanentIfItStillExists(game);
        if (tappedCreature == null || sourceCreature == null) {
            return false;
        }
        game.addEffect(new ZygonInfiltratorCopyEffect(sourceCreature, tappedCreature), source);
        return true;
    }
}

class ZygonInfiltratorCopyEffect extends CopyEffect {

    ZygonInfiltratorCopyEffect(Permanent sourceCreature, Permanent tappedCreature) {
        super(Duration.Custom, tappedCreature, sourceCreature.getId());
    }

    private ZygonInfiltratorCopyEffect(final ZygonInfiltratorCopyEffect effect) {
        super(effect);
    }

    @Override
    public ZygonInfiltratorCopyEffect copy() {
        return new ZygonInfiltratorCopyEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game)) {
            return true;
        }
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        return targetPermanent == null || !targetPermanent.isTapped();
    }
}
