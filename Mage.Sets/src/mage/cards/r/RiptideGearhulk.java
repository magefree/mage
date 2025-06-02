package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author Jmlundeen
 */
public final class RiptideGearhulk extends CardImpl {

    public RiptideGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{W}{U}{U}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // When this creature enters, for each opponent, put up to one target nonland permanent that player controls into its owner's library third from the top.
        Effect effect = new PutIntoLibraryNFromTopTargetEffect(3)
                .setText("for each opponent, put up to one target nonland permanent that player controls into its owner's library third from the top")
                .setTargetPointer(new EachTargetPointer());
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);
    }

    private RiptideGearhulk(final RiptideGearhulk card) {
        super(card);
    }

    @Override
    public RiptideGearhulk copy() {
        return new RiptideGearhulk(this);
    }
}
