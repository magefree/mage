
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Simown
 */
public final class SamitePilgrim extends CardImpl {

    public SamitePilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Domain - {T}: Prevent the next X damage that would be dealt to target creature this turn, where X is the number of basic land types among lands you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SamitePilgrimPreventDamageToTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability.addHint(DomainHint.instance));
    }

    private SamitePilgrim(final SamitePilgrim card) {
        super(card);
    }

    @Override
    public SamitePilgrim copy() {
        return new SamitePilgrim(this);
    }
}

class SamitePilgrimPreventDamageToTargetEffect extends PreventionEffectImpl {

    public SamitePilgrimPreventDamageToTargetEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, true);
        staticText = "Prevent the next X damage that would be dealt to target creature this turn, where X is the number of basic land types among lands you control.";
    }

    public SamitePilgrimPreventDamageToTargetEffect(final SamitePilgrimPreventDamageToTargetEffect effect) {
        super(effect);
    }

    @Override
    public SamitePilgrimPreventDamageToTargetEffect copy() {
        return new SamitePilgrimPreventDamageToTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        amountToPrevent = DomainValue.REGULAR.calculate(game, source, this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getFirstTarget());
    }

}
