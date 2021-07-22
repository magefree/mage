package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BloodsoakedChampion extends CardImpl {

    public BloodsoakedChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bloodstained Brave can't block.
        this.addAbility(new CantBlockAbility());

        // <i>Raid</i> &mdash; {1}{B}: Return Bloodstained Brave from your graveyard to the battlefield. Activate this ability only if you attacked this turn.
        Ability ability = new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new ManaCostsImpl<>("{1}{B}"),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; {1}{B}: Return {this} from your graveyard to the battlefield. Activate only if you attacked this turn.");
        ability.setAbilityWord(AbilityWord.RAID);
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private BloodsoakedChampion(final BloodsoakedChampion card) {
        super(card);
    }

    @Override
    public BloodsoakedChampion copy() {
        return new BloodsoakedChampion(this);
    }
}
