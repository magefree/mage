package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfessionalWrestler extends CardImpl {

    public ProfessionalWrestler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // This creature can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()));
    }

    private ProfessionalWrestler(final ProfessionalWrestler card) {
        super(card);
    }

    @Override
    public ProfessionalWrestler copy() {
        return new ProfessionalWrestler(this);
    }
}
