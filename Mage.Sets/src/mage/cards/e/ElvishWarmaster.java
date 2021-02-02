package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ElfToken;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class ElvishWarmaster extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "one or more other Elves");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.ELF, "Elves");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ElvishWarmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more other Elves enters the battlefield under your control, create a 1/1 green Elf Warrior creature token. This ability triggers only once each turn.
        this.addAbility(new ElvishWarmasterTriggeredAbility(new CreateTokenEffect(new ElfToken()), filter));

        // {5}{G}{G}: Elves you control get +2/+2 and gain deathtouch until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn, filter2).setText("Elves you control get +2/+2"), new ManaCostsImpl("{5}{G}{G}")
        );
        ability.addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, filter2
                ).setText("and gain deathtouch until end of turn")
        );
        this.addAbility(ability);
    }

    private ElvishWarmaster(final ElvishWarmaster card) {
        super(card);
    }

    @Override
    public ElvishWarmaster copy() {
        return new ElvishWarmaster(this);
    }
}

class ElvishWarmasterTriggeredAbility extends EntersBattlefieldControlledTriggeredAbility {

    public ElvishWarmasterTriggeredAbility(Effect effect, FilterPermanent filter) {
        super(effect, filter);
    }

    public ElvishWarmasterTriggeredAbility(final ElvishWarmasterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Integer lastTurnTriggered = (Integer) game.getState()
                .getValue(CardUtil.getCardZoneString("lastTurnTriggered" + originalId, sourceId, game));
        if (lastTurnTriggered != null && lastTurnTriggered == game.getTurnNum()) {
            return false;
        }
        return super.checkTrigger(event, game);
    }

    @Override
    public void trigger(Game game, UUID controllerId) {
        game.getState().setValue(CardUtil
                .getCardZoneString("lastTurnTriggered" + originalId, sourceId, game), game.getTurnNum());
        super.trigger(game, controllerId);
    }

    @Override
    public String getRule() {
        return "Whenever one or more other Elves enter the battlefield under your control, " +
                "create a 1/1 green Elf Warrior creature token. This ability triggers only once each turn.";
    }

    @Override
    public ElvishWarmasterTriggeredAbility copy() {
        return new ElvishWarmasterTriggeredAbility(this);
    }
}
