
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author LevelX2
 */
public final class BirthingHulk extends CardImpl {

    public BirthingHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.ELDRAZI, SubType.DRONE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When Birthing Hulk enters the battlefield, create two 1/1 colorless Eldrazi Scion creature tokens. They have "Sacrifice this creature: Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken(), 2);
        effect.setText("create two 1/1 colorless Eldrazi Scion creature tokens. They have \"Sacrifice this creature: Add {C}.\"");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));

        // {1}{C}: Regenerate Birthing Hulk.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{C}")));

    }

    private BirthingHulk(final BirthingHulk card) {
        super(card);
    }

    @Override
    public BirthingHulk copy() {
        return new BirthingHulk(this);
    }
}
