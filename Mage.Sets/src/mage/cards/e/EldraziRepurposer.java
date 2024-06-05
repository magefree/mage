package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EldraziRepurposer extends CardImpl {

    public EldraziRepurposer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell and when Eldrazi Repurposer dies, create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new OrTriggeredAbility(
                Zone.ALL,
                new CreateTokenEffect(new EldraziSpawnToken()),
                new CastSourceTriggeredAbility(null, false),
                new DiesSourceTriggeredAbility(null, false)
        ).setTriggerPhrase("When you cast this spell and when {this} dies, "));
    }

    private EldraziRepurposer(final EldraziRepurposer card) {
        super(card);
    }

    @Override
    public EldraziRepurposer copy() {
        return new EldraziRepurposer(this);
    }
}
