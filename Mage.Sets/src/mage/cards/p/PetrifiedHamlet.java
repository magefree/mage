package mage.cards.p;

import java.util.Optional;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ChosenNamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class PetrifiedHamlet extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("lands with the chosen name");

    static {
        filter.add(ChosenNamePredicate.instance);
    }

    public PetrifiedHamlet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // When this land enters, choose a land card name.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.LAND_NAME)));

        // Activated abilities of sources with the chosen name can't be activated unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(new PetrifiedHamletCostEffect()));

        // Lands with the chosen name have "{T}: Add {C}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            new ColorlessManaAbility(), Duration.WhileOnBattlefield, filter
        )));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private PetrifiedHamlet(final PetrifiedHamlet card) {
        super(card);
    }

    @Override
    public PetrifiedHamlet copy() {
        return new PetrifiedHamlet(this);
    }
}

class PetrifiedHamletCostEffect extends ContinuousRuleModifyingEffectImpl {

    PetrifiedHamletCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated unless they're mana abilities";
    }

    private PetrifiedHamletCostEffect(final PetrifiedHamletCostEffect effect) {
        super(effect);
    }

    @Override
    public PetrifiedHamletCostEffect copy() {
        return new PetrifiedHamletCostEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        if (ability.isPresent() && object != null) {
            return game.getState().getPlayersInRange(source.getControllerId(), game).contains(event.getPlayerId()) // controller in range
                    && !ability.get().isManaAbility()
                    && CardUtil.haveSameNames(object, cardName, game);
        }
        return false;
    }
}
