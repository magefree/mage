package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.*;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Skiwkr
 */
public final class RoryWilliams extends CardImpl {

    public RoryWilliams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Amy Pond
        this.addAbility(new PartnerWithAbility("Amy Pond"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // The Last Centurion -- When you cast this spell from anywhere other than exile, exile it with three time counters on it. It gains suspend. Then investigate.
        Ability ability = new RoryWilliamsTriggeredAbility(new ExileSpellWithTimeCountersEffect(3, true)
                .setText("exile it with three time counters on it. It gains suspend"));
        ability.addEffect(new InvestigateEffect(1).concatBy("Then"));
        ability.withFlavorWord("The Last Centurion");
        this.addAbility(ability);
    }

    private RoryWilliams(final RoryWilliams card) {
        super(card);
    }

    @Override
    public RoryWilliams copy() {
        return new RoryWilliams(this);
    }
}

class RoryWilliamsTriggeredAbility extends CastSourceTriggeredAbility {

    RoryWilliamsTriggeredAbility(Effect effect) {
        super(effect, false);
        setRuleAtTheTop(true);
        setTriggerPhrase("When you cast this spell from anywhere other than exile, ");
    }

    private RoryWilliamsTriggeredAbility(final RoryWilliamsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RoryWilliamsTriggeredAbility copy() {
        return new RoryWilliamsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event,game)
                && !((Spell) game.getObject(sourceId)).getFromZone().equals(Zone.EXILED);
    }
}
