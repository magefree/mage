package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Skiwkr
 */
public final class TheForetoldSoldier extends CardImpl {

    public TheForetoldSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // The Foretold Soldier must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // The Foretold Soldier can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneSourceEffect()));

        // Whenever The Foretold Soldier deals damage, exile it face down. It becomes foretold.
        this.addAbility(new DealsDamageTriggeredAbility(new ForetellAbility.ForetellExileEffect(this, "{1}{G}","{2}").setText("exile it face down. It becomes foretold.")));
        Ability ability = new SimpleStaticAbility(Zone.ALL, new ForetellAbility.ForetellLookAtCardEffect());
        ability.setControllerId(ownerId);  // if not set, anyone can look at the card in exile
        this.addAbility(ability);

        // Foretell {1}{G}
        this.addAbility(new ForetellAbility(this, "{1}{G}"));

    }

    private TheForetoldSoldier(final TheForetoldSoldier card) {
        super(card);
    }

    @Override
    public TheForetoldSoldier copy() {
        return new TheForetoldSoldier(this);
    }
}
