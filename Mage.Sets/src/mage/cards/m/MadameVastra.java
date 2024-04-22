package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MadameVastra extends CardImpl {

    public MadameVastra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Jenny Flint
        this.addAbility(new PartnerWithAbility("Jenny Flint"));

        // Madame Vastra must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Whenever a creature dealt damage by Madame Vastra this turn dies, create a Clue token and a Food token.
        TriggeredAbility trigger = new DealtDamageAndDiedTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken()));
        trigger.addEffect(new CreateTokenEffect(new FoodToken()).setText("and a Food token"));
        this.addAbility(trigger);
    }

    private MadameVastra(final MadameVastra card) {
        super(card);
    }

    @Override
    public MadameVastra copy() {
        return new MadameVastra(this);
    }
}
