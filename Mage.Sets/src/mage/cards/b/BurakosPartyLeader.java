package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.HasSubtypesSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurakosPartyLeader extends CardImpl {

    public BurakosPartyLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Burakos, Party Leader is also a Cleric, Rogue, Warrior, and Wizard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new HasSubtypesSourceEffect(
                SubType.CLERIC, SubType.ROGUE, SubType.WARRIOR, SubType.WIZARD
        )));

        // Whenever Burakos, Party Leader attacks, defending player loses X life and you create X Treasure tokens, where X is the number of creatures in your party.
        Ability ability = new AttacksTriggeredAbility(
                new LoseLifeTargetEffect(PartyCount.instance)
                        .setText("defending player loses X life"),
                false, null, SetTargetPointer.PLAYER
        );
        ability.addEffect(new CreateTokenEffect(
                new TreasureToken(), PartyCount.instance
        ).setText("and you create X Treasure tokens, " +
                "where X is the number of creatures in your party"));
        this.addAbility(ability.addHint(PartyCountHint.instance));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private BurakosPartyLeader(final BurakosPartyLeader card) {
        super(card);
    }

    @Override
    public BurakosPartyLeader copy() {
        return new BurakosPartyLeader(this);
    }
}
