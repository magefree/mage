package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;
import mage.constants.Duration;

/**
 * @author TheElk801
 */
public final class CanopyStalker extends CardImpl {

    public CanopyStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Canopy Stalker must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // When Canopy Stalker dies, you gain 1 life for each creature that died this turn.
        this.addAbility(new DiesSourceTriggeredAbility(
                new GainLifeEffect(CreaturesDiedThisTurnCount.instance)
                        .setText("you gain 1 life for each creature that died this turn")
        ).addHint(CreaturesDiedThisTurnHint.instance));
    }

    private CanopyStalker(final CanopyStalker card) {
        super(card);
    }

    @Override
    public CanopyStalker copy() {
        return new CanopyStalker(this);
    }
}
