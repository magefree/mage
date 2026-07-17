package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallousInspector extends CardImpl {

    public CallousInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature dies, it deals 1 damage to you. Create a Clue token.
        Ability ability = new DiesSourceTriggeredAbility(new DamageControllerEffect(1, "it"));
        ability.addEffect(new CreateTokenEffect(new ClueArtifactToken()));
        this.addAbility(ability);
    }

    private CallousInspector(final CallousInspector card) {
        super(card);
    }

    @Override
    public CallousInspector copy() {
        return new CallousInspector(this);
    }
}
