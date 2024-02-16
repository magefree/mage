package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadeyePlunderers extends CardImpl {

    public DeadeyePlunderers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deadeye Plunderers gets +1/+1 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(ArtifactYouControlCount.instance, ArtifactYouControlCount.instance, Duration.WhileOnBattlefield)
        ).addHint(ArtifactYouControlHint.instance));

        // {2}{U}{B}: Create a colorless artifact token named Treasure with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()), new ManaCostsImpl<>("{2}{U}{B}")));
    }

    private DeadeyePlunderers(final DeadeyePlunderers card) {
        super(card);
    }

    @Override
    public DeadeyePlunderers copy() {
        return new DeadeyePlunderers(this);
    }
}
