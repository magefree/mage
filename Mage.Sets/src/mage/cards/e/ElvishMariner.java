package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishMariner extends CardImpl {

    public ElvishMariner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Elvish Mariner attacks, scry 1.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(1, false)));

        // Whenever you scry, tap up to X target nonland permanents, where X is the number of cards looked at while scrying this way.
        this.addAbility(new ScryTriggeredAbility(new TapTargetEffect()
                .setText("tap up to X target nonland permanents, where X is " +
                        "the number of cards looked at while scrying this way"))
                .setTargetAdjuster(ElvishMarinerAdjuster.instance));
    }

    private ElvishMariner(final ElvishMariner card) {
        super(card);
    }

    @Override
    public ElvishMariner copy() {
        return new ElvishMariner(this);
    }
}

enum ElvishMarinerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int amount = ability
                .getEffects()
                .stream()
                .mapToInt(effect -> (Integer) effect.getValue("amount"))
                .findFirst()
                .orElse(0);
        ability.getTargets().clear();
        ability.addTarget(new TargetNonlandPermanent(0, amount));
    }
}
