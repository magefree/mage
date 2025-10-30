package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArgivianAvenger extends CardImpl {

    public ArgivianAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {1}: Until end of turn, Argivian Avenger gets -1/-1 and gains your choice of flying, vigilance, deathtouch, or haste.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(-1, -1, Duration.EndOfTurn)
                .setText("Until end of turn, {this} gets -1/-1"), new GenericManaCost(1));
        ability.addEffect(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source, "", false,
                FlyingAbility.getInstance(), VigilanceAbility.getInstance(), DeathtouchAbility.getInstance(), HasteAbility.getInstance())
                .concatBy("and"));
        this.addAbility(ability);
    }

    private ArgivianAvenger(final ArgivianAvenger card) {
        super(card);
    }

    @Override
    public ArgivianAvenger copy() {
        return new ArgivianAvenger(this);
    }
}
